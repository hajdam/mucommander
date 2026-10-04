<?php
// MySQL DB Library

error_reporting(E_ALL & ~E_NOTICE);

if (!function_exists('DB_Init')) {

$cfg['dbhost']='localhost';
$cfg['dbuname']='db_user_name';
$cfg['dbpass']='db_password';
$cfg['dbname']='db_name';
$cfg['dbprefix']='';

$DB_LastResults = array();

function DB_Init($host,$user,$password,$name)
{
  global $db_link, $DB_Tables;
  $db_link = mysqli_connect($host,$user,$password, $name);
  if(mysqli_connect_errno()==1049) {
	db_query("CREATE DATABASE $name");
	$db_link = mysqli_connect($host,$user,$password, $name);
  }
}

function DB_Close() {
  global $db_link;
  if ($db_link) mysqli_close($db_link);
}

function DB_Query($query)
{
  global $db_result,$db_link,$DB_LastResults;
  //echo('DB: Request('.$query.')<br/>');
  //$DB_LastResults[0] = mysqli_query($db_link, $query);
  //System_ShowArray($DB_LastResults);
  $db_result = mysqli_query($db_link, $query);
  if(mysqli_error($db_link)) echo('DB: Request error number '.mysqli_errno($db_link).'!('.mysqli_error($db_link).')<br>Request: '.$query.'<br/>');
}

function DB_Row()
{
  global $db_result;
  return(mysqli_fetch_array($db_result));
}

function DB_SimpleQuery($query)
{
  global $db_result,$DB_LastResults;
  DB_Query($query);
  if (@$db_result) { return(mysqli_fetch_array($db_result)); } else return array();
}

function DB_NumRows()
{
  global $db_result;
  return(mysqli_num_rows($db_result));
}

function DB_Save()
{
  global $db_result,$DB_LastResults;
  array_push($DB_LastResults,$db_result);
  //System_ShowArray($DB_LastResults);
}

function DB_Load()
{
  global $db_result,$DB_LastResults;
  $db_result = array_pop($DB_LastResults);
}

function DB_Insert($table,$data)
{
  $name = '';
  $values = '';
  foreach($data as $key => $value)
  {
    $value = strtr($value,'"','\"');
    $name .= ",".$key;
    if($value=='NOW()') $values .= ",".$value;
    else $values .= ',"'.$value.'"';
  }
  $name = substr($name,1);
  $values = substr($values,1);
  db_query("INSERT INTO $table ($name) VALUES($values)");
  //echo("INSERT INTO $table ($name) VALUES($values)");
}

function DB_Update($table,$condition,$data)
{
  $name = '';
  $values = '';
  foreach($data as $key => $value)
  {
    $value = strtr($value,'"','\"');
    if($value!='NOW()') $value = '"'.$value.'"';
    $values .= ", ".$key."=".$value;
  }
  $values = substr($values,2);
  DB_Query("UPDATE $table SET $values WHERE ($condition)");
  //echo("DB_Update: UPDATE $table SET $values WHERE ($condition)\n");
}

DB_Init($cfg['dbhost'],$cfg['dbuname'],$cfg['dbpass'],$cfg['dbprefix'].$cfg['dbname']);
DB_Query("SET NAMES 'utf8'");

} ?>
