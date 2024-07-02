@extends('layouts.app')

@section('title', 'Absen Masuk')

@section('content')
    <section class="section">
        <div class="card">
            <div class="card-header">
                <a href="{{ route('check-ins.index') }}" class="btn btn-primary">Kembali</a>
            </div>
            <div class="card-body">
                <h4>Barcode</h4>

                {!! QrCode::size(350)->generate($barcode->data()['kode']) !!}
            </div>
        </div>
    </section>
@endsection
