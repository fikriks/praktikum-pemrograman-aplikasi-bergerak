@extends('layouts.app')

@section('title', 'Tambah Siswa')

@section('content')
    <section class="section">
        <div class="card">
            <div class="card-header">
                <a href="{{ route('students.index') }}" class="btn btn-primary">Kembali</a>
            </div>
            <div class="card-body">
                <form action="{{ route('students.store') }}" method="POST" class="form form-horizontal">
                    @csrf

                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-4">
                                <label for="nama">Nama Siswa <span class="text-danger">*</span></label>
                            </div>
                            <div class="col-md-8 form-group">
                                <input type="text" id="nama"
                                    class="form-control @error('nama') is-invalid @enderror" name="nama"
                                    placeholder="Nama" value="{{ old('nama') }}" required>

                                @error('nama')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                            <div class="col-md-4">
                                <label for="kelas">Kelas <span class="text-danger">*</span></label>
                            </div>
                            <div class="col-md-8 form-group">
                                <input type="text" id="kelas"
                                    class="form-control @error('kelas') is-invalid @enderror" name="kelas"
                                    placeholder="Kelas" value="{{ old('kelas') }}" required>

                                @error('kelas')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                            <div class="col-md-4">
                                <label for="email">Email <span class="text-danger">*</span></label>
                            </div>
                            <div class="col-md-8 form-group">
                                <input type="email" id="email"
                                    class="form-control @error('email') is-invalid @enderror" name="email"
                                    placeholder="Email" value="{{ old('email') }}" required>

                                @error('email')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                            <div class="col-md-4">
                                <label for="password">Password <span class="text-danger">*</span></label>
                            </div>
                            <div class="col-md-8 form-group">
                                <input type="password" id="password"
                                    class="form-control @error('password') is-invalid @enderror" name="password"
                                    placeholder="Password" required>

                                @error('password')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>
                            <div class="col-sm-12 d-flex justify-content-end">
                                <button type="submit" class="btn btn-primary me-1 mb-1">Submit</button>
                                <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
@endsection
