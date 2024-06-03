<?php
header("Content-Type: application/json");

include 'db_config.php'; // Pastikan file ini berisi informasi koneksi database Anda

// Memeriksa apakah metode yang digunakan adalah HTTP DELETE
if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
    $url = $_SERVER['REQUEST_URI'];
    $parts = explode('/', $url);
    $userId = end($parts);

    // Validasi data
    if (empty($userId)) {
        echo json_encode(["error" => "Invalid input: ID is required"]);
        exit;
    }

    // Menyiapkan query DELETE menggunakan prepared statements
    $sql = "DELETE FROM users WHERE id='$userId'";

    if ($koneksi->query($sql) === TRUE) {
        echo json_encode(["success" => true, "message" => "User deleted successfully"]);
    } else {
        echo json_encode(["error" => $koneksi->error]);
    }

    $koneksi->close();
} else {
    echo json_encode(["error" => "Invalid request method"]);
}
?>