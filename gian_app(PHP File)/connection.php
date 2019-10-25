<?php 
$conn = mysqli_connect("localhost","root","","android");






if($conn)
{
	echo json_encode(array('response'=>'Connetced'));
	$upload_data = $_POST["data"];
	$name = $_POST["name"];
	$email = $_POST["email"];
	$phno = $_POST["phno"];
	$occ = $_POST["occupation"];
	$innovation = $_POST["idea"];
	
	$sql  = "insert into ideas (Name,Email,Contact_Number,Occupation,idea) values ('$name','$email','$phno','$occ','$innovation')";
	$upload_path = "upload/$phno";
	
	
	if(mysqli_query($conn,$sql)
	{
		//file_put_contents($upload_path,base64_decode($upload_data));
		echo json_encode(array('response'=>'Data uploaded'));
	}
	else
	{
		echo json_encode(array('response'=>'Data uploaded fail'));
	}
}
else
{
		echo json_encode(array('response'=>'Data uploaded'));	
}
	mysqli_close($conn);


?>
      