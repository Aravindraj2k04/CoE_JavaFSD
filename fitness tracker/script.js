document.addEventListener("DOMContentLoaded", () => {
    const routineForm = document.getElementById("routineForm");
    const routineList = document.getElementById("routineList");

    if (routineForm) {
        routineForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const exerciseName = document.getElementById("exerciseName").value;
            const duration = document.getElementById("duration").value;

            if (exerciseName && duration) {
                addRoutine(exerciseName, duration);
                saveRoutine(exerciseName, duration);
                routineForm.reset();
            }
        });
    }

    function addRoutine(name, duration) {
        const li = document.createElement("li");
        li.textContent = `${name} - ${duration} mins`;
        routineList.appendChild(li);
    }

    function saveRoutine(name, duration) {
        let routines = JSON.parse(localStorage.getItem("routines")) || [];
        routines.push({ name, duration });
        localStorage.setItem("routines", JSON.stringify(routines));
    }

    function loadRoutines() {
        let routines = JSON.parse(localStorage.getItem("routines")) || [];
        if (routines.length === 0) {
            // Adding some dummy data if no routines exist
            routines = [
                { name: "Push-ups", duration: "10" },
                { name: "Squats", duration: "15" },
                { name: "Plank", duration: "5" }
            ];
            localStorage.setItem("routines", JSON.stringify(routines));
        }
        routines.forEach((routine) => addRoutine(routine.name, routine.duration));
    }

    if (routineList) {
        loadRoutines();
    }
});
