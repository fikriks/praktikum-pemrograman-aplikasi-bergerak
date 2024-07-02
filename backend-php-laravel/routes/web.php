<?php

use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return to_route('login');
});

Route::get('login', [App\Http\Controllers\Auth\LoginController::class, 'showLoginForm'])->name('login');
Route::post('login', [App\Http\Controllers\Auth\LoginController::class, 'login']);
Route::post('logout', [App\Http\Controllers\Auth\LoginController::class, 'logout'])->name('logout');

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');

Route::get('/jurnals', [App\Http\Controllers\JurnalController::class, 'index'])->name('jurnals.index');
Route::get('/check-ins', [App\Http\Controllers\CheckInController::class, 'index'])->name('check-ins.index');
Route::get('/check-ins/create', [App\Http\Controllers\CheckInController::class, 'create'])->name('check-ins.create');
Route::resource('students', App\Http\Controllers\StudentController::class)->except('show');
