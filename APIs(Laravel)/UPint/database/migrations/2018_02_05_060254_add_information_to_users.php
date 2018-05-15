<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddInformationToUsers extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('users', function($table) {
        $table->string('first_name');
        $table->string('last_name');
        $table->string('phone_number');
        $table->string('school');
        $table->string('major');
        $table->string('Date_graduated');

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
        $table->dropColumn('first_name');
        $table->dropColumn('last_name');
        $table->dropColumn('phone_number');
        $table->dropColumn('school');
        $table->dropColumn('major');
        $table->dropColumn('Date_graduated');
    });
    }
}
