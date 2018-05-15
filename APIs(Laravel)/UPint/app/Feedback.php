<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Feedback extends Model
{
    protected $fillable = ['user_id', 'comment'];

    protected $hidden = [
        'created_at','updated_at',
    ];

    protected $table = 'feedback';
}
