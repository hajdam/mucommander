<?php
// Store referer
$referer = @$_SERVER['HTTP_REFERER'];
$component = "";

function startsWith($text, $match) {
    return substr($text, 0, strlen($match)) === $match;
}

$query = @$_GET['f'];
if (empty($query)) {
  $query = $_SERVER['QUERY_STRING'];
}

$query = str_replace('..','',$query);
$query = str_replace('/','',$query);
$component = ':' . $query;

if (!empty($query)) {
  header('Location: ' . $query);
  exit();
}
?>