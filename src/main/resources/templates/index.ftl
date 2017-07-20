<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Uploading Files Example with Spring Boot, Freemarker</title>
</head>

<body>
<form name="uploadingForm" enctype="multipart/form-data" action="/upload" method="POST">
    <p>
        <input id="fileInput" type="file" name="uploadingFile" />
    </p>
    <p>
        <input type="submit" value="Upload files">
    </p>
    <p>
    ${message!}
    </p>
</form>
</body>
</html>