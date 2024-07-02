@extends('layouts.app')

@section('title', 'Dashboard')

@section('content')
    <section class="section">
        <div class="card">
            <div class="card-body">
                <p>Halo, Selamat datang {{ auth()->user()->name }}</p>
            </div>
        </div>
    </section>
@endsection
