<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Courses_user extends Model
{
    protected $fillable = ['user_id', 'course_id'];

    protected $hidden = [
        'created_at','updated_at',
    ];
}
