<?php global $prefix, $submenu_editor;
$prefix = '..';

$query = @$_GET['p'];
if (empty($query)) {
  $query = @getenv('QUERY_STRING');
  $paramEndPos = strpos($query, '&');
  $valuePos = strpos($query, '=');
  if (!empty($query) && ($paramEndPos == null || ($paramEndPos > 0 && ($valuePos == null || $valuePos > $paramEndPos)))) {
    header('Location: ?p='.$query);
    exit();
  }
}

$nomenu = true;
include('header.php');
include('db.php');

if ($query) {
  global $db_link;
  $module_id = $db_link -> real_escape_string($query);
  $rows = DB_Query('SELECT addon.id, addon.module_id, addon.name, addon.description, addon.homepage, addon.icon, addon.created, addon.owner_id, addon.created, user.name AS owner_name FROM addon INNER JOIN user ON user.id = addon.owner_id WHERE addon.module_id = \''.$module_id.'\' ORDER BY addon.id');
  $row = DB_Row();
?>
<a href="index.php">&lt;&lt;&nbsp;Back</a>
<div>
<?php
  if ($row) {
    $icon = $row['icon'];
    if (!$icon) {
    	$icon = "icon/default.png";
    } else {
    	$icon = "icon/".$icon;
    }
    	
  	echo "<h1 id=\"addons\"><img src=\"".$icon."\" alt=\"[ICON]\" style=\"float:left;margin-right:10px\" />Addon: ".$row['name']."</h1>\n";
  	echo "<p>Description: ".$row['description']."<br/>\n";
  	echo "Module ID: ".$row['module_id']."<br/>\n";
  	echo "Homepage: <a href=\"".$row['homepage']."\">".$row['homepage']."</a><br/>\n";
  	echo "Owner: ".$row['owner_name']."<br/>\n";
  	echo "Created: ".$row['created']."<br/>\n";
  	echo "</p>\n";
  	
  	// Long description
  	$textfile = 'text/'.$row['module_id'].'.dat';
  	if (file_exists($textfile)) {
  		include $textfile;
  	}
  	
  	// Releases
  	echo "<p>Releases:<br/>\n";
  	$rels = DB_Query('SELECT version, sequence, created, addon_rel.file AS file, license.id AS license, license.name AS license_name FROM addon_rel LEFT JOIN license ON license.id = addon_rel.license_id WHERE addon_id = '.$row['id'].' ORDER BY sequence DESC');
    while ($rel = DB_Row()) {
    	$license = "";
    	if ($rel['license']) {
    		$license = " / ".$rel['license_name'];
    	}
    	echo "* <a href=\"download/?f=".$rel['file']."\">Version ".$rel['version']."</a> (".$rel['created'].$license.")<br/> \n";
    }
  	echo "</p>\n";
  	
  	// Dependencies
  	echo "<p>Dependencies:<br/>\n";
    $deps = DB_Query('SELECT addon_dep.id, addon_dep.type, addon_dep.value FROM addon_dep, addon_rel WHERE addon_dep.addon_rel_id = addon_rel.id AND addon_rel.addon_id = '.$row['id'].' AND NOT EXISTS(SELECT 1 FROM addon_rel rl WHERE rl.addon_id = addon_rel.addon_id AND rl.sequence > addon_rel.sequence) ORDER BY addon_dep.id');
    while ($dep = DB_Row()) {
    	switch ($dep['type']) {
    	  case 0:
    	  	echo "* Module ".$dep['value']."<br/> \n";
    	  	break;
    	  case 1:
    	  	echo "* Library ".$dep['value']."<br/> \n";
    	  	break;
    	  case 2:
    	  	echo "* Maven Library ".$dep['value']."<br/> \n";
    	  	break;
    	}
    }
  	echo "</p>\n";
  } else {
  	echo '<p>Error: No addon exist for ID</p>';
  }

  DB_Close();
} else {
?>
<a href="..">&lt;&lt;&nbsp;Back</a>
<div>

<h1 id="addons">Addons: Browse</h1>
<?php
  $rows = DB_Query('SELECT id, module_id, name, description, icon, created FROM addon ORDER BY created DESC');
  while ($row = DB_Row()) {
    $icon = $row['icon'];
    if (!$icon) {
    	$icon = "icon/default.png";
    } else {
    	$icon = "icon/".$icon;
    }

	echo "<div><img src=\"".$icon."\" alt=\"[ICON]\" style=\"float:left;margin-right:10px\" /><a href=\"?p=".$row['module_id']."\"><strong>".$row['name']."</strong></a><br/>".$row['description']."</div><br/>\n";
  }

  DB_Close();
}
?>
</div>
<?php include '../refer.php'; ?>