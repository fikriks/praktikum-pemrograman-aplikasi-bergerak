@extends('layouts.app')

@section('title', 'Siswa')

@section('content')
    <section class="section">
        <div class="card">
            <div class="card-header">
                <a href="{{ route('students.create') }}" class="btn btn-primary">Tambah Data</a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table" id="table1">
                        <thead>
                            <tr>
                                <th>Nama</th>
                                <th>Kelas</th>
                                <th>Email</th>
                                <th>Aksi</th>
                            </tr>
                        </thead>
                        <tbody>
                            @foreach ($students as $student)
                                <tr>
                                    <td>{{ $student['nama'] }}</td>
                                    <td>{{ $student['kelas'] }}</td>
                                    <td>{{ $student['email'] }}</td>
                                    <td><a href="{{ route('students.edit', $student['user_id']) }}"
                                            class="btn btn-warning text-white"><i class="bi bi-pencil-fill"></i></a>
                                        <button class="btn btn-danger delete-confirm"
                                            data-action="{{ route('students.destroy', $student['user_id']) }}"><i
                                                class="bi bi-trash-fill"></i></button>
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

@section('js')
    <script>
        $("#table1").DataTable({
            responsive: !0,
            drawCallback: function() {
                $(".delete-confirm").on("click", function(a) {
                    a.preventDefault(),
                        Swal.fire({
                            title: "Apakah anda yakin?",
                            text: "Jika data dihapus maka data yang bersangkutan akan ikut terhapus juga.",
                            icon: "warning",
                            showCancelButton: !0,
                            confirmButtonColor: "#3085d6",
                            cancelButtonColor: "#d33",
                            confirmButtonText: "Ya, hapus!",
                            cancelButtonText: "Batal",
                        }).then((a) => {
                            if (a.isConfirmed) {
                                let a = $(this).attr("data-action"),
                                    t = jQuery('meta[name="csrf-token"]').attr(
                                        "content"
                                    );
                                $("body").html(
                                        "<form class='form-inline remove-form' method='post' action='" +
                                        a +
                                        "'></form>"
                                    ),
                                    $("body")
                                    .find(".remove-form")
                                    .append(
                                        '<input name="_method" type="hidden" value="delete">'
                                    ),
                                    $("body")
                                    .find(".remove-form")
                                    .append(
                                        '<input name="_token" type="hidden" value="' +
                                        t +
                                        '">'
                                    ),
                                    $("body").find(".remove-form").submit();
                            }
                        });
                });
            },
        });
    </script>
@endsection
