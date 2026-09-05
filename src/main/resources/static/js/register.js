document
    .getElementById("registerForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const username =
            document.getElementById("username").value.trim();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value;

        const confirmPassword =
            document.getElementById("confirmPassword").value;

        const message =
            document.getElementById("message");


        // Check passwords
        if (password !== confirmPassword) {

            message.textContent =
                "Passwords do not match.";

            return;
        }


        try {

            const response = await fetch(
                "/api/auth/register",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({
                        username: username,
                        email: email,
                        password: password
                    })
                }
            );


            const data = await response.text();


            if (response.ok) {

                message.textContent =
                    "Registration successful!";

                document.getElementById(
                    "registerForm"
                ).reset();


                // Go to login page
                setTimeout(function () {

                    window.location.href = "/login";

                }, 1000);

            } else {

                message.textContent =
                    data || "Registration failed.";

            }

        } catch (error) {

            console.error(error);

            message.textContent =
                "Server connection failed.";

        }

    });