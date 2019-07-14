$(document).ready(function () {

  $(document).on('click', ".translateBtn", function (event) {
    var params = new Object();
    params.text = $("#fromText").val();
    params.from = "ru";
    params.to = "en";

    $.ajax({
      type: "POST",
      url: "/translate",
      data: JSON.stringify(params),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success:
        function (data) {
          // alert(data);
          $("#toText").val(data.text);
          $("#copyright").html(data.copyright);
        },
      error: function (e) {
        console.log("ERROR: ", e);
      },
      done: function (e) {
        console.log("DONE");
      }
    });
    event.preventDefault(); // avoid to execute the actual submit of the form.
  });
});
