document.addEventListener("DOMContentLoaded", function () {

// =========================================================
// JWT TOKEN
// =========================================================

    const token = localStorage.getItem("token");


// =========================================================
// HTML ELEMENTS
// =========================================================

    const dressForm =
        document.getElementById("dressForm");

    const formTitle =
        document.getElementById("formTitle");

    const submitBtn =
        document.getElementById("submitBtn");

    const cancelBtn =
        document.getElementById("cancelBtn");

    const message =
        document.getElementById("message");

    const dressContainer =
        document.getElementById("dressContainer");

    const logoutBtn =
        document.getElementById("logoutBtn");

    const adminInfo =
        document.getElementById("adminInfo");

    const refreshBtn =
        document.getElementById("refreshBtn");


// =========================================================
// EDITING STATE
// =========================================================

    let editingDressId = null;


// =========================================================
// CHECK TOKEN
// =========================================================

    if (!token) {

        window.location.href = "/login";

        return;
    }


// =========================================================
// CHECK JWT
// =========================================================

    try {

        const payload =
            JSON.parse(
                atob(token.split(".")[1])
            );


        const username =
            payload.sub ||
            payload.username ||
            "Admin";


        adminInfo.textContent =
            "Welcome, " + username;


    } catch (error) {

        console.error(
            "Invalid JWT token:",
            error
        );

        localStorage.removeItem("token");

        window.location.href = "/login";

        return;
    }


// =========================================================
// LOAD DRESSES
// =========================================================

    loadDresses();


// =========================================================
// ADD / UPDATE DRESS
// =========================================================

    dressForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            clearMessage();


            // -------------------------------------------------
            // GET FORM VALUES
            // -------------------------------------------------

            const name =
                document
                    .getElementById("name")
                    .value
                    .trim();


            const price =
                parseFloat(
                    document
                        .getElementById("price")
                        .value
                );


            const description =
                document
                    .getElementById("description")
                    .value
                    .trim();


            const size =
                document
                    .getElementById("size")
                    .value
                    .trim();


            const color =
                document
                    .getElementById("color")
                    .value
                    .trim();


            const image =
                document
                    .getElementById("image")
                    .value
                    .trim();


            const available =
                document
                    .getElementById("available")
                    .checked;


            // -------------------------------------------------
            // VALIDATION
            // -------------------------------------------------

            if (
                !name ||
                !description ||
                !size ||
                !color ||
                isNaN(price) ||
                price <= 0
            ) {

                showMessage(
                    "Please fill all required fields correctly.",
                    "error"
                );

                return;
            }


            // -------------------------------------------------
            // REQUEST BODY
            // -------------------------------------------------

            const dressData = {

                name: name,

                price: price,

                description: description,

                size: size,

                color: color,

                image: image,

                available: available

            };


            // -------------------------------------------------
            // DETERMINE ADD OR UPDATE
            // -------------------------------------------------

            let url = "/api/dresses";

            let method = "POST";


            if (editingDressId !== null) {

                url =
                    "/api/dresses/" +
                    editingDressId;

                method = "PUT";
            }


            // -------------------------------------------------
            // SEND REQUEST
            // -------------------------------------------------

            try {

                const response =
                    await fetch(url, {

                        method: method,

                        headers: {

                            "Content-Type":
                                "application/json",

                            "Authorization":
                                "Bearer " + token

                        },

                        body:
                            JSON.stringify(dressData)

                    });


                // -------------------------------------------------
                // 401
                // -------------------------------------------------

                if (response.status === 401) {

                    handleUnauthorized();

                    return;
                }


                // -------------------------------------------------
                // 403
                // -------------------------------------------------

                if (response.status === 403) {

                    showMessage(
                        "Access denied. Admin permission required.",
                        "error"
                    );

                    return;
                }


                // -------------------------------------------------
                // READ RESPONSE
                // -------------------------------------------------

                const data =
                    await readResponse(response);


                // -------------------------------------------------
                // SUCCESS
                // -------------------------------------------------

                if (response.ok) {

                    if (editingDressId !== null) {

                        showMessage(
                            "Wedding dress updated successfully!",
                            "success"
                        );

                    } else {

                        showMessage(
                            "Wedding dress added successfully!",
                            "success"
                        );
                    }


                    resetForm();

                    await loadDresses();


                } else {

                    showMessage(
                        getErrorMessage(data),
                        "error"
                    );
                }


            } catch (error) {

                console.error(
                    "Error saving dress:",
                    error
                );

                showMessage(
                    "Server error. Please try again.",
                    "error"
                );

            }

        }
    );


// =========================================================
// CANCEL EDIT
// =========================================================

    cancelBtn.addEventListener(
        "click",
        function () {

            resetForm();

            clearMessage();

        }
    );


// =========================================================
// REFRESH
// =========================================================

    refreshBtn.addEventListener(
        "click",
        function () {

            loadDresses();

        }
    );


// =========================================================
// LOGOUT
// =========================================================

    logoutBtn.addEventListener(
        "click",
        function () {

            localStorage.removeItem("token");
            localStorage.removeItem("username");
            localStorage.removeItem("role");

            window.location.href = "/login";

        }
    );


// =========================================================
// LOAD ALL DRESSES
// =========================================================

    async function loadDresses() {

        dressContainer.innerHTML =
            "<p>Loading dresses...</p>";


        try {

            const response =
                await fetch(
                    "/api/dresses",
                    {

                        method: "GET",

                        headers: {

                            "Authorization":
                                "Bearer " + token

                        }

                    }
                );


            // -------------------------------------------------
            // 401
            // -------------------------------------------------

            if (response.status === 401) {

                handleUnauthorized();

                return;
            }


            // -------------------------------------------------
            // 403
            // -------------------------------------------------

            if (response.status === 403) {

                dressContainer.innerHTML =
                    `<div class="error-box">
                    Access denied.
                </div>`;

                return;
            }


            // -------------------------------------------------
            // READ RESPONSE
            // -------------------------------------------------

            const data =
                await readResponse(response);


            if (!response.ok) {

                dressContainer.innerHTML =
                    `<div class="error-box">
                    Failed to load dresses.
                </div>`;

                console.error(
                    "Failed to load dresses:",
                    data
                );

                return;
            }


            // -------------------------------------------------
            // EXTRACT DATA
            // -------------------------------------------------

            let dresses = data;


            // Support CommonResponse
            if (
                data &&
                typeof data === "object" &&
                data.body !== undefined
            ) {

                dresses = data.body;
            }


            if (!dresses) {

                dresses = [];

            }


            if (!Array.isArray(dresses)) {

                dresses = [dresses];

            }


            // -------------------------------------------------
            // EMPTY
            // -------------------------------------------------

            if (dresses.length === 0) {

                dressContainer.innerHTML =
                    `<div class="empty-box">
                    No wedding dresses found.
                </div>`;

                return;
            }


            // -------------------------------------------------
            // CLEAR
            // -------------------------------------------------

            dressContainer.innerHTML = "";


            // -------------------------------------------------
            // DISPLAY
            // -------------------------------------------------

            dresses.forEach(
                function (dress) {

                    createDressCard(dress);

                }
            );


        } catch (error) {

            console.error(
                "Error loading dresses:",
                error
            );

            dressContainer.innerHTML =
                `<div class="error-box">
                Unable to connect to server.
            </div>`;

        }

    }


// =========================================================
// CREATE DRESS CARD
// =========================================================

    function createDressCard(dress) {

        const card =
            document.createElement("div");

        card.className =
            "dress-card";


        // -------------------------------------------------
        // IMAGE
        // -------------------------------------------------

        if (dress.image) {

            const image =
                document.createElement("img");

            image.src =
                dress.image;

            image.alt =
                dress.name ||
                "Wedding Dress";

            image.className =
                "dress-image";


            image.onerror =
                function () {

                    image.style.display =
                        "none";

                };


            card.appendChild(image);

        }


        // -------------------------------------------------
        // INFORMATION
        // -------------------------------------------------

        const info =
            document.createElement("div");

        info.className =
            "dress-info";


        const title =
            document.createElement("h3");

        title.textContent =
            dress.name || "";

        info.appendChild(title);


        const price =
            document.createElement("p");

        price.innerHTML =
            "<strong>Price:</strong> Rs. " +
            Number(dress.price || 0)
                .toLocaleString(
                    "en-LK",
                    {
                        minimumFractionDigits: 2
                    }
                );

        info.appendChild(price);


        const description =
            document.createElement("p");

        description.innerHTML =
            "<strong>Description:</strong> " +
            escapeHtml(
                dress.description || ""
            );

        info.appendChild(description);


        const size =
            document.createElement("p");

        size.innerHTML =
            "<strong>Size:</strong> " +
            escapeHtml(
                dress.size || ""
            );

        info.appendChild(size);


        const color =
            document.createElement("p");

        color.innerHTML =
            "<strong>Color:</strong> " +
            escapeHtml(
                dress.color || ""
            );

        info.appendChild(color);


        const available =
            document.createElement("p");

        available.innerHTML =
            "<strong>Available:</strong> " +
            (
                dress.available
                    ? '<span class="available">Yes</span>'
                    : '<span class="not-available">No</span>'
            );

        info.appendChild(available);


        // -------------------------------------------------
        // BUTTONS
        // -------------------------------------------------

        const buttons =
            document.createElement("div");

        buttons.className =
            "card-buttons";


        const editBtn =
            document.createElement("button");

        editBtn.type =
            "button";

        editBtn.className =
            "edit-btn";

        editBtn.textContent =
            "Edit";


        editBtn.addEventListener(
            "click",
            function () {

                editDress(dress);

            }
        );


        const deleteBtn =
            document.createElement("button");

        deleteBtn.type =
            "button";

        deleteBtn.className =
            "delete-btn";

        deleteBtn.textContent =
            "Delete";


        deleteBtn.addEventListener(
            "click",
            function () {

                deleteDress(dress.id);

            }
        );


        buttons.appendChild(editBtn);
        buttons.appendChild(deleteBtn);


        info.appendChild(buttons);

        card.appendChild(info);

        dressContainer.appendChild(card);

    }


// =========================================================
// EDIT DRESS
// =========================================================

    function editDress(dress) {

        editingDressId =
            dress.id;


        formTitle.textContent =
            "Update Wedding Dress";


        submitBtn.textContent =
            "Update Dress";


        submitBtn.classList.add(
            "update-mode"
        );


        cancelBtn.style.display =
            "inline-block";


        document.getElementById("name").value =
            dress.name || "";


        document.getElementById("price").value =
            dress.price || "";


        document.getElementById("description").value =
            dress.description || "";


        document.getElementById("size").value =
            dress.size || "";


        document.getElementById("color").value =
            dress.color || "";


        document.getElementById("image").value =
            dress.image || "";


        document.getElementById("available").checked =
            dress.available === true;


        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    }


// =========================================================
// DELETE DRESS
// =========================================================

    async function deleteDress(id) {

        const confirmed =
            confirm(
                "Are you sure you want to delete this wedding dress?"
            );


        if (!confirmed) {

            return;

        }


        try {

            const response =
                await fetch(
                    "/api/dresses/" + id,
                    {

                        method: "DELETE",

                        headers: {

                            "Authorization":
                                "Bearer " + token

                        }

                    }
                );


            // -------------------------------------------------
            // 401
            // -------------------------------------------------

            if (response.status === 401) {

                handleUnauthorized();

                return;
            }


            // -------------------------------------------------
            // 403
            // -------------------------------------------------

            if (response.status === 403) {

                showMessage(
                    "Access denied. Admin permission required.",
                    "error"
                );

                return;
            }


            // -------------------------------------------------
            // SUCCESS
            // -------------------------------------------------

            if (response.ok) {

                showMessage(
                    "Wedding dress deleted successfully!",
                    "success"
                );


                if (
                    editingDressId !== null &&
                    Number(editingDressId) === Number(id)
                ) {

                    resetForm();

                }


                await loadDresses();


            } else {

                const data =
                    await readResponse(response);


                showMessage(
                    getErrorMessage(data),
                    "error"
                );

            }


        } catch (error) {

            console.error(
                "Error deleting dress:",
                error
            );

            showMessage(
                "Server error. Please try again.",
                "error"
            );

        }

    }


// =========================================================
// RESET FORM
// =========================================================

    function resetForm() {

        editingDressId =
            null;


        dressForm.reset();


        formTitle.textContent =
            "Add New Wedding Dress";


        submitBtn.textContent =
            "Add Dress";


        submitBtn.classList.remove(
            "update-mode"
        );


        cancelBtn.style.display =
            "none";


        document.getElementById("available").checked =
            true;

    }


// =========================================================
// UNAUTHORIZED
// =========================================================

    function handleUnauthorized() {

        localStorage.removeItem("token");
        localStorage.removeItem("username");
        localStorage.removeItem("role");

        window.location.href =
            "/login";

    }


// =========================================================
// MESSAGE
// =========================================================

    function showMessage(
        text,
        type
    ) {

        message.textContent =
            text;

        message.className =
            type;

    }


    function clearMessage() {

        message.textContent =
            "";

        message.className =
            "";

    }


// =========================================================
// READ RESPONSE SAFELY
// =========================================================

    async function readResponse(response) {

        const text =
            await response.text();


        if (!text) {

            return null;

        }


        try {

            return JSON.parse(text);

        } catch {

            return text;

        }

    }


// =========================================================
// ERROR MESSAGE
// =========================================================

    function getErrorMessage(data) {

        if (!data) {

            return "Request failed.";

        }


        if (typeof data === "string") {

            return data;

        }


        if (data.message) {

            return data.message;

        }


        if (data.body && data.body.message) {

            return data.body.message;

        }


        if (data.errors) {

            return Object.values(data.errors)
                .join(", ");

        }


        return "Request failed.";

    }


// =========================================================
// HTML ESCAPE
// =========================================================

    function escapeHtml(value) {

        const div =
            document.createElement("div");

        div.textContent =
            value;

        return div.innerHTML;

    }

});