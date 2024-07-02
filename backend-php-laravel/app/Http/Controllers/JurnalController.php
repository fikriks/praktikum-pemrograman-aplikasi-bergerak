<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

class JurnalController extends Controller
{
    protected $firestoreDB;

    public function __construct()
    {
        $this->firestoreDB = app('firebase.firestore')->database();
    }

    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $documents = $this->firestoreDB->collection('jurnal')->documents();

        $jurnals = [];
        foreach ($documents as $doc) {
            if ($doc->exists()) {
                $entry = $doc->data();
                $userId = $entry['user_id'];

                $userDoc = $this->firestoreDB->collection('users')->document($userId)->snapshot();

                if ($userDoc->exists()) {
                    $userData = $userDoc->data();
                    $entry['nama'] = $userData['nama'];
                    $entry['kelas'] = $userData['kelas'];
                }

                $jurnals[] = $entry;
            }
        }

        return view('jurnals.index', compact('jurnals'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
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
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}
