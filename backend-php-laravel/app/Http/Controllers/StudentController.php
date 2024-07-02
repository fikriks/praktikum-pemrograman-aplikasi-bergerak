<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use RealRashid\SweetAlert\Facades\Alert;

class StudentController extends Controller
{
    protected $auth;
    protected $firestoreDB;

    public function __construct()
    {
        $this->auth = app('firebase.auth');
        $this->firestoreDB = app('firebase.firestore')->database();
    }

    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $documents = $this->firestoreDB->collection('users')->documents();
        $students = [];

        foreach ($documents as $document) {
            if ($document->exists()) {
                $userData = $document->data();

                try {
                    $firebaseUser = $this->auth->getUser($userData['user_id']);
                    $userData['email'] = $firebaseUser->email;
                } catch (UserNotFound $e) {
                    $userData['email'] = 'Email not found';
                } catch (\Exception $e) {
                    $userData['email'] = 'Error retrieving email';
                }

                $students[] = $userData;
            }
        }

        return view('students.index', compact('students'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        return view('students.create');
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $request->validate([
            'nama' => 'required',
            'kelas' => 'required',
            'email' => 'required|email',
            'password' => 'required|min:8'
        ]);

        $userProperties = [
            'email' => $request->input('email'),
            'password' => $request->input('password')
        ];

        try {
            $existingUser = $this->auth->getUserByEmail($request->input('email'));

            Alert::error('Error', 'Email tersebut sudah ditambahkan');
            return redirect()->back()->withInput();
        } catch (\Kreait\Firebase\Exception\Auth\UserNotFound $e) {
            try {
                $user = $this->auth->createUser($userProperties);

                $this->firestoreDB->collection('users')->document($user->uid)->set([
                    'user_id' => $user->uid,
                    'nama' => $request->input('nama'),
                    'kelas' => $request->input('kelas')
                ]);

                Alert::success('Success', 'Berhasil menambahkan data');
                return to_route('students.index');
            } catch (\Exception $e) {
                Alert::error('Error', 'Gagal menambahkan data: ' . $e->getMessage());
                return redirect()->back()->withInput();
            }
        } catch (\Exception $e) {
            Alert::error('Error', 'Terjadi kesalahan: ' . $e->getMessage());
            return redirect()->back()->withInput();
        }
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        $document = $this->firestoreDB->collection('users')->document($id)->snapshot();

        if (!$document->exists()) {
            abort(404);
        }

        $student = $document->data();

        try {
            $firebaseUser = $this->auth->getUser($student['user_id']);
            $student['email'] = $firebaseUser->email;
        } catch (UserNotFound $e) {
            $student['email'] = 'Email not found';
        } catch (\Exception $e) {
            $student['email'] = 'Error retrieving email';
        }

        return view('students.edit', compact('student'));
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $request->validate([
            'nama' => 'required',
            'kelas' => 'required',
            'email' => 'required|email',
            'password' => 'nullable|min:8'
        ]);

        $document = $this->firestoreDB->collection('users')->document($id)->snapshot();

        if (!$document->exists()) {
            Alert::error('Error', 'User not found');
            return redirect()->back();
        }

        $userData = $document->data();

        $userProperties = [
            'email' => $request->input('email')
        ];

        if ($request->filled('password')) {
            $userProperties['password'] = $request->input('password');
        }

        try {
            $this->auth->updateUser($userData['user_id'], $userProperties);

            $this->firestoreDB->collection('users')->document($id)->update([
                ['path' => 'nama', 'value' => $request->input('nama')],
                ['path' => 'kelas', 'value' => $request->input('kelas')],
                ['path' => 'email', 'value' => $request->input('email')]
            ]);

            Alert::success('Success', 'Data berhasil diperbarui');
            return to_route('students.index');
        } catch (\Exception $e) {
            Alert::error('Error', 'Gagal memperbarui data: ' . $e->getMessage());
            return redirect()->back()->withInput();
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        try {
            $document = $this->firestoreDB->collection('users')->document($id)->snapshot();

            if (!$document->exists()) {
                Alert::error('Error', 'User not found');
                return redirect()->back();
            }

            $userData = $document->data();
            $userId = $userData['user_id'];

            $this->auth->deleteUser($userId);

            $this->firestoreDB->collection('users')->document($id)->delete();

            $jurnalDocuments = $this->firestoreDB->collection('jurnal')->where('user_id', '=', $userId)->documents();
            foreach ($jurnalDocuments as $jurnalDocument) {
                if ($jurnalDocument->exists()) {
                    $this->firestoreDB->collection('jurnal')->document($jurnalDocument->id())->delete();
                }
            }

            $absenDocuments = $this->firestoreDB->collection('absen-masuk')->where('user_id', '=', $userId)->documents();
            foreach ($absenDocuments as $absenDocument) {
                if ($absenDocument->exists()) {
                    $this->firestoreDB->collection('absen-masuk')->document($absenDocument->id())->delete();
                }
            }

            Alert::success('Success', 'Data berhasil dihapus');
        } catch (\Exception $e) {
            Alert::error('Error', 'Gagal menghapus data: ' . $e->getMessage());
        }

        return to_route('students.index');
    }
}
