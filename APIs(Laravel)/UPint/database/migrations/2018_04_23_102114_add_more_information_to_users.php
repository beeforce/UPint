<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddMoreInformationToUsers extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('users', function($table) {
        $table->string('location_city');
        $table->string('university_can_teach');
        $table->string('id_card');
        $table->string('introduce');

    });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('users', function($table) {
        $table->dropColumn('location_city');
        $table->dropColumn('university_can_teach');
        $table->dropColumn('id_card');
        $table->dropColumn('introduce');
    });
    }
}
