@extends('layouts.app')

@section('title', 'Absen Masuk')

@section('content')
    <section class="section">
        <div class="card">
            <div class="card-header">
                <a href="{{ route('check-ins.create') }}" class="btn btn-primary">Generate QRCode</a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table" id="table">
                        <thead>
                            <tr>
                                <th>Nama</th>
                                <th>Kelas</th>
                                <th>Tanggal</th>
                            </tr>
                        </thead>
                        <tbody>
                            @foreach ($checkIns as $checkIn)
                                <tr>
                                    <td>{{ $checkIn['nama'] ?? '-' }}</td>
                                    <td>{{ $checkIn['kelas'] ?? '-' }}</td>
                                    <td>{{ $checkIn['jam'] . ', ' . $checkIn['created_at'] }}</td>
                                </tr>
                            @endforeach
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
@endsection
