$(document).ready(function () {
    reloadActual();

    $('#addMtg').click(() => {
        addMeeting();
    });

    setInterval(howMuchWatches, 100);
});

function formatLocal(date) {
    var localDf = new Intl.DateTimeFormat("ru", {
        weekday: "short",
        hour: "numeric",
        minute: "numeric",
        timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone
    });

    return localDf.format(date);
}

function formatNy(date) {
    var nyDf = new Intl.DateTimeFormat("en-US", {
        weekday: "short",
        hour: "numeric",
        minute: "numeric",
        timeZone: "America/New_York"
    });

    return nyDf.format(date);
}

function reloadActual() {

    $.get("/api/meeting/actual", function (data) {
        fillMeetingTable(data);
    });
}

function addMeeting() {
    let time = new Date($("#mtgTime").val()).toISOString();

    $.ajax({
        type: "POST",
        url: "/api/meeting/add",
        data: JSON.stringify({description: $("#mtgDescr").val(), meetingTimeDate: time, meetingTimeOffsetDateTime: time}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){reloadActual()},
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}


function howMuchWatches() {
    var local = $('#nowlocal');
    var ny = $('#nowny');

    let now = new Date();

    local.text(formatLocal(now));
    ny.text(formatNy(now));
}

function dropMeeting(id) {
    $.ajax({
        type: "DELETE",
        url: "/api/meeting/delete/" + id,
        data: JSON.stringify({}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){reloadActual()},
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}

function fillMeetingTable(meetings) {
    var mtgtbody = $('#meetingsTbody');

    mtgtbody.empty();

    meetings.forEach((m, i) => {
        let removeBtn =  $('<a class="btn btn-danger" role="button">X</a>')
            .click(() => {
                dropMeeting(m.id);
            });

        let tr = $('<tr>')
            .append($('<td>').text(m.description))
            .append($('<td>').text(formatLocal(Date.parse(m.meetingTimeDate))))
            .append($('<td>').text(formatLocal(Date.parse(m.meetingTimeOffsetDateTime))))
            .append($('<td>').text(formatNy(Date.parse(m.meetingTimeOffsetDateTime))))
            .append($('<td>').text(formatNy(Date.parse(m.meetingTimeOffsetDateTime))))
            .append($('<td>').append(removeBtn));

        mtgtbody.append(tr);

    });
}