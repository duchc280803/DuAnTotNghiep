var isHienThi = false;

function toggleNoiDung() {
    var noiDung = document.getElementById("noiDung");
    isHienThi = !isHienThi;
    if (isHienThi) {
        noiDung.style.display = "block";
    } else {
        noiDung.style.display = "none";
    }
}