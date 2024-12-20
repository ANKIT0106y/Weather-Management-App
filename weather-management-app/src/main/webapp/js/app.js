document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const cityInput = document.querySelector("#city");
    const resultContainer = document.querySelector(".result");

    // Add event listener for form submission
    form.addEventListener("submit", function (event) {
        const cityName = cityInput.value.trim();

        // Validate city input
        if (!cityName) {
            event.preventDefault();
            alert("Please enter a city name!");
            return;
        }

        // Clear previous results
        resultContainer.innerHTML = "";
    });

    // Add dynamic validation for city input
    cityInput.addEventListener("input", function () {
        const cityName = cityInput.value.trim();
        if (cityName.length < 3) {
            cityInput.style.borderColor = "red";
        } else {
            cityInput.style.borderColor = "green";
        }
    });

    // Add interactivity (clear result on new input)
    cityInput.addEventListener("focus", function () {
        resultContainer.innerHTML = "";
    });
});