
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
    <div class="h3 mb-0">Liste Lieu</div>
</div>
<%@page import="voyage.Lieu, java.util.List"%>
<%
List<Lieu> l = (List<Lieu>)request.getAttribute("result");
%>

<!-- form -->
<div>
      <div class="table-responsive">
    <table class="table">
        <th>Id</th>
        <th>Nom</th>
        <% for(Lieu ls : l){ %>
        <tr>
            <td>
                <%=ls.getIdLieu()%>
            </td>
            
            <td>
                <%=ls.getNomLieu()%>
            </td>
        </tr>
        <% } %>
    </table>
  </div>
</div>
<!-- End form -->