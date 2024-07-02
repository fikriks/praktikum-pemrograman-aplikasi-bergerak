<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use Kreait\Firebase\Auth as FirebaseAuth;
use Carbon\Carbon;
use Kreait\Firebase\Factory;
use Kreait\Firebase\Firestore;

class CheckInController extends Controller
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
        $documents = $this->firestoreDB->collection('absen-masuk')->documents();

        $checkIns = [];
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

                $checkIns[] = $entry;
            }
        }

        return view('check-ins.index', compact('checkIns'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        $collection = $this->firestoreDB->collection('barcode-absen-masuk');
        $documents = $collection->documents();

        foreach ($documents as $document) {
            if ($document->exists()) {
                $document->reference()->delete();
            }
        }

        $this->firestoreDB->collection('barcode-absen-masuk')->add([
            'kode' => base64_encode(Carbon::now()->format('Y-m-d'))
        ]);

        $barcode = $this->firestoreDB->collection('barcode-absen-masuk')->limit(1)->documents()->rows()[0];

        return view('check-ins.create', compact('barcode'));
    }
}
