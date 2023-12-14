<form action="GetCondServlet" method="post">
    <div class="form-row">
        <div class="input-group">
            <select class="custom-select">
                <option>Liste bouquet</option>
              </select>
            <input class="btn btn-sm btn-primary  form-control-icon-text" placeholder="Search" type="submit">
        </div>
  </div>

</form>

<div class="mb-3 mb-md-4 d-flex justify-content-between">
    <div class="h3 mb-0">Liste CategorieLieu</div>
</div>
<%@page import="voyage.CategorieLieu, java.util.List"%>
<%
List<CategorieLieu> l = (List<CategorieLieu>)request.getAttribute("result");
%>

<!-- form -->
<div>
      <div class="table-responsive">
    <table class="table">
        <th>Id</th>
        <th>Nom</th>
        <% for(CategorieLieu ls : l){ %>
        <tr>
            <td>
                <%=ls.getIdCategorieLieu()%>
            </td>
            
            <td>
                <%=ls.getNomCategorieLieu()%>
            </td>
        </tr>
        <% } %>
    </table>
  </div>
</div>
<!-- End form -->