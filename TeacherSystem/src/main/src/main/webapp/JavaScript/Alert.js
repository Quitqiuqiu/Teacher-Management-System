//消息通知自动移除
document.addEventListener("DOMContentLoaded", function () {
    let alertBox = document.getElementById("alertBox");
    if (alertBox) {
        setTimeout(function () {
            alertBox.style.opacity = "0"; 
            setTimeout(() => alertBox.style.display = "none", 500);
        }, 5000);
    }
});