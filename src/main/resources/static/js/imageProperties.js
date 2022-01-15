
$(function () {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(document).ready(function() {
    $('#loader').hide();
    $("#submit").on("click", function() {
        $("#submit").prop("disabled", true);
        let height = $("#height").val();
        let file = $("#image").val();
        let width = $("#width").val();
        let mirrorX = $("#mirrorX").val();
        let mirrorY = $("#mirrorY").val();
        let color = $("#color").val();
        let form = $("#form").serialize();

        let data = new FormData($("#form")[0]);
        data.append('height', height);
        data.append('width', width);
        data.append('mirrorX', mirrorX);
        data.append('mirrorY', mirrorY);
        data.append('color', color);
        data.append("image",file );
        $('#loader').show();
        if (height === "" || file === "" || width === "") {
            $("#submit").prop("disabled", false);
            $('#loader').hide();
            $("#width").css("border-color", "red");
            $("#height").css("border-color", "red");
            $("#image").css("border-color", "red");
            $("#error_width").html("Please fill the required field.");
            $("#error_file").html("Please fill the required field.");
            $("#error_height").html("Please fill the required field.");
        } else {
            $("#height").css("border-color", "");
            $("#image").css("border-color", "");
            $("#width").css("border-color", "");
           // $("#description").css("border-color", "");
            $('#error_height').css('opacity', 0);
            $('#error_file').css('opacity', 0);
            $('#error_width').css('opacity', 0);
           // $('#error_description').css('opacity', 0);


            console.log(data)

            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                data: data,
                url: "/upload",
                processData: false,
                contentType: false,
                cache: false,
                success: function(data, statusText, xhr) {
                    if(xhr.status == "200") {
                        $('#loader').hide();
                        $("#form")[0].reset();
                        $('#success').css('display','block');
                        $("#error").text("");
                        $("#success").html("Image Inserted Succsessfully.");
                        $('#success').delay(2000).fadeOut('slow');


                    }
                },
                error: function(e) {
                    $('#loader').hide();
                    $('#error').css('display','block');
                    $("#error").html("Oops! something went wrong.");
                    $('#error').delay(5000).fadeOut('slow');
                   // location.reload();
                }
            });
        }
    });
});

