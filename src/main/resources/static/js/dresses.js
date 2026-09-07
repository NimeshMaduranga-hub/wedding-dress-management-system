document.addEventListener("DOMContentLoaded", function () {

    console.log("DRESSES JS LOADED");

    const token =
        localStorage.getItem("token");

    const username =
        localStorage.getItem("username");

    const role =
        localStorage.getItem("role");

    const container =
        document.getElementById("dressContainer");

    const userInfo =
        document.getElementById("userInfo");

    const logoutBtn =
        document.getElementById("logoutBtn");


    /*
     * Check Login
     */

    if (!token) {

        window.location.href =
            "/login";

        return;
    }


    /*
     * Display User Information
     */

    userInfo.textContent =
        (username || "User") +
        " | " +
        (role || "USER");


    /*
     * Load Wedding Dresses
     */

    loadDresses();


    async function loadDresses() {

        container.innerHTML =
            `
        <div class="loading-box">
            Loading dresses...
        </div>
        `;

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


            /*
             * Unauthorized
             */

            if (response.status === 401) {

                localStorage.removeItem("token");
                localStorage.removeItem("username");
                localStorage.removeItem("role");

                window.location.href =
                    "/login";

                return;
            }


            /*
             * Forbidden
             */

            if (response.status === 403) {

                container.innerHTML =
                    `
                <div class="error-box">
                    You do not have permission
                    to view wedding dresses.
                </div>
                `;

                return;
            }


            /*
             * Other errors
             */

            if (!response.ok) {

                throw new Error(
                    "Failed to load dresses"
                );

            }


            /*
             * Get JSON
             */

            const dresses =
                await response.json();


            /*
             * Clear container
             */

            container.innerHTML =
                "";


            /*
             * No dresses
             */

            if (
                !Array.isArray(dresses) ||
                dresses.length === 0
            ) {

                container.innerHTML =
                    `
                <div class="empty-box">
                    No wedding dresses available.
                </div>
                `;

                return;
            }


            /*
             * Create cards
             */

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

            container.innerHTML =
                `
            <div class="error-box">
                Failed to load wedding dresses.
                Please try again.
            </div>
            `;
        }
    }


    /*
     * Create Dress Card
     */

    function createDressCard(dress) {

        const card =
            document.createElement("div");

        card.className =
            "dress-card";


        /*
         * Image
         */

        const image =
            document.createElement("img");

        image.className =
            "dress-image";

        image.alt =
            dress.name ||
            "Wedding Dress";


        if (dress.image) {

            image.src =
                dress.image;

        } else {

            image.src =
                "/images/dress-placeholder.jpg";
        }


        /*
         * If image cannot load
         */

        image.onerror =
            function () {

                image.src =
                    "/images/dress-placeholder.jpg";

            };


        card.appendChild(image);


        /*
         * Dress Info
         */

        const info =
            document.createElement("div");

        info.className =
            "dress-info";


        /*
         * Name
         */

        const name =
            document.createElement("h2");

        name.textContent =
            dress.name ||
            "Unnamed Dress";

        info.appendChild(name);


        /*
         * Description
         */

        const description =
            document.createElement("p");

        description.className =
            "description";

        description.textContent =
            dress.description ||
            "No description available.";

        info.appendChild(description);


        /*
         * Price
         */

        const price =
            document.createElement("p");

        price.className =
            "price";

        const formattedPrice =
            Number(dress.price || 0)
                .toLocaleString(
                    "en-LK",
                    {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    }
                );

        price.textContent =
            "Rs. " +
            formattedPrice;

        info.appendChild(price);


        /*
         * Details
         */

        const details =
            document.createElement("div");

        details.className =
            "details";


        const size =
            document.createElement("p");

        size.innerHTML =
            "<strong>Size:</strong> ";

        const sizeValue =
            document.createElement("span");

        sizeValue.textContent =
            dress.size || "-";

        size.appendChild(sizeValue);


        const color =
            document.createElement("p");

        color.innerHTML =
            "<strong>Color:</strong> ";

        const colorValue =
            document.createElement("span");

        colorValue.textContent =
            dress.color || "-";

        color.appendChild(colorValue);


        details.appendChild(size);
        details.appendChild(color);

        info.appendChild(details);


        /*
         * Availability
         */

        const availability =
            document.createElement("span");


        if (dress.available === true) {

            availability.className =
                "available";

            availability.textContent =
                "Available";

        } else {

            availability.className =
                "unavailable";

            availability.textContent =
                "Not Available";
        }


        info.appendChild(
            availability
        );


        /*
         * Add info to card
         */

        card.appendChild(info);


        /*
         * Add card to container
         */

        container.appendChild(card);
    }


    /*
     * Logout
     */

    logoutBtn.addEventListener(
        "click",
        function () {

            localStorage.removeItem("token");
            localStorage.removeItem("username");
            localStorage.removeItem("role");

            window.location.href =
                "/login";

        }
    );

});