<!DOCTYPE html>
<html>
<head>
  <title>Product List</title>
  <style>
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid black;
      padding: 8px;
      text-align: left;
    }
    th {
      background-color: #f2f2f2;
    }
  </style>
</head>
<body>
<h1>Product List</h1>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Price</th>
  </tr>
  </thead>
  <tbody>
  <#list products as product>
    <tr>
      <td>${product.id}</td>
      <td>${product.name}</td>
      <td>${product.price}</td>
    </tr>
  </#list>
  </tbody>
</table>

<form action="addProduct.html">
  <input type="submit" value="Add Product">
</form>
</body>
</html>
