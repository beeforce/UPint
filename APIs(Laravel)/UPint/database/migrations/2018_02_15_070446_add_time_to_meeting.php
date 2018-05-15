<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddTimeToMeeting extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('meetings', function($table) {
        $table->date('date');
        $table->time('start_time');
        $table->time('finish_time');

    });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('meetings', function($table) {
        $table->dropColumn('date');
        $table->dropColumn('start_time');
        $table->dropColumn('finish_time');
    });
    }
}
