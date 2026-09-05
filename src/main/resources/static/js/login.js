document
    .getElementById("loginForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const username =
            document.getElementById("username").value.trim();

        const password =
            document.getElementById("password").value;

        const message =
            document.getElementById("message");

        try {

            const response = await fetch(
                "/api/auth/login",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({
                        username: username,
                        password: password
                    })
                }
            );

            const data = await response.json();

            if (response.ok) {

                // Save JWT information
                localStorage.setItem("token", data.token);
                localStorage.setItem("username", data.username);
                localStorage.setItem("role", data.role);

                message.textContent =
                    "Login successful!";

                // Role-based redirect
                if (data.role === "ADMIN") {

                    window.location.href =
                        "/admin-dashboard";

                } else {

                    window.location.href = "/";

                }

            } else {

                message.textContent =
                    data.message || "Login failed.";

            }

        } catch (error) {

            console.error(error);

            message.textContent =
                "Server connection failed.";
        }
    });