$(document).ready(function () {

    $('.task').bind('dragstart', function (event) {
        event.originalEvent.dataTransfer.setData("text/plain", event.target.getAttribute('id'));
    });

    $('.status')
        .bind('dragover', function (event) {
            event.preventDefault();
        })
        .bind('drop', function (event) {
            var taskCardId = event.originalEvent.dataTransfer.getData("text/plain");
            var targetSection = event.target;
            var taskCard = document.getElementById(taskCardId);
            $.ajax({
                type: "POST",
                url: "/tasks",
                data: JSON.stringify({ id: parseInt(taskCard.id), status: targetSection.id }),
                contentType: 'application/json'
            });
            targetSection.appendChild(taskCard);
            event.preventDefault();
        });
});