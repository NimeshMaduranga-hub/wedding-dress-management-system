console.log("DRESSES JS LOADED");

const token = localStorage.getItem("token");
const username = localStorage.getItem("username");
const role = localStorage.getItem("role");


// Check login

if (!token) {

    window.location.href = "/login";

}


// Display user

document.getElementById("userInfo").textContent =
    username + " | " + role;


// Load dresses

async function loadDresses() {

    const container =
        document.getElementById("dressContainer");

    try {

        const response = await fetch(
            "/api/dresses",
            {
                method: "GET",

                headers: {
                    "Authorization":
                        "Bearer " + token
                }
            }
        );


        if (response.status === 401) {

            localStorage.clear();

            window.location.href = "/login";

            return;
        }


        if (!response.ok) {

            throw new Error(
                "Failed to load dresses"
            );

        }


        const dresses =
            await response.json();


        container.innerHTML = "";


        if (dresses.length === 0) {

            container.innerHTML =
                "<p>No wedding dresses available.</p>";

            return;
        }


        dresses.forEach(function (dress) {

            const card =
                document.createElement("div");

            card.className = "dress-card";


            const image =
                dress.image
                    ? dress.image
                    : "/images/dress-placeholder.jpg";


            const availability =
                dress.available
                    ? `<span class="available">
                         Available
                       </span>`
                    : `<span class="unavailable">
                         Not Available
                       </span>`;


            card.innerHTML = `

                <img
                    src="${image}"
                    alt="${dress.name}"
                    class="dress-image"
                >

                <div class="dress-info">

                    <h2>
                        ${dress.name}
                    </h2>

                    <p class="description">
                        ${dress.description}
                    </p>

                    <p class="price">
                        Rs. ${dress.price}
                    </p>

                    <div class="details">

                        <strong>Size:</strong>
                        ${dress.size}

                        <br>

                        <strong>Color:</strong>
                        ${dress.color}

                    </div>

                    ${availability}

                </div>

            `;


            container.appendChild(card);

        });


    } catch (error) {

        console.error(error);

        container.innerHTML =
            "<p>Failed to load wedding dresses.</p>";

    }

}


// Logout

document
    .getElementById("logoutBtn")
    .addEventListener("click", function () {

        localStorage.removeItem("token");
        localStorage.removeItem("username");
        localStorage.removeItem("role");

        window.location.href = "/login";

    });


// Start

loadDresses();