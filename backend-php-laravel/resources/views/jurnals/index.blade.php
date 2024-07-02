@extends('layouts.app')

@section('title', 'Jurnal')

@section('content')
    <section class="section">
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table" id="table">
                        <thead>
                            <tr>
                                <th>Nama</th>
                                <th>Kelas</th>
                                <th>Jurnal</th>
                                <th>Tanggal</th>
                            </tr>
                        </thead>
                        <tbody>
                            @foreach ($jurnals as $jurnal)
                                <tr>
                                    <td>{{ $jurnal['nama'] ?? '-' }}</td>
                                    <td>{{ $jurnal['kelas'] ?? '-' }}</td>
                                    <td>{{ $jurnal['description'] }}</td>
                                    <td>{{ \Carbon\Carbon::parse($jurnal['created_at_timestamp'])->isoFormat('HH:mm:ss, DD MMMM YYYY') }}
                                    </td>
                                </tr>
                            @endforeach
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
@endsection
