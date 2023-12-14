<%@page import="voyage.Activite, voyage.Bouquet, java.util.List"%>
<%
List<Bouquet> b = (List<Bouquet>)request.getAttribute("allbouquet");
%>
<form action="GetCondServlet?page=listActivite.jsp" method="post">
    <div class="form-row">
        <div class="input-group">
            
            <select name="bouquet" class="custom-select">
                <option>Liste bouquet</option>
                <%for(Bouquet bouquet : b) {%>
                <option value="<%=bouquet.getIdBouquet()%>"><%=bouquet.getNomBouquet()%></option>
                <% } %>
              </select>
            
            <input class="btn btn-sm btn-primary  form-control-icon-text" placeholder="Search" type="submit">
        </div>
  </div>

</form>

<div class="mb-3 mb-md-4 d-flex justify-content-between">
    <div class="h3 mb-0">Liste Activite</div>
</div>

<%
List<Activite> l = (List<Activite>)request.getAttribute("result");
%>

<!-- form -->
<div>
      <div class="table-responsive">
    <table class="table">
        <th>Id</th>
        <th>Nom</th>
        <th>Etat</th>
        <th>Action</th>

        <% for(Activite ls : l){ %>
        <tr>
            <td><%=ls.getIdActivite()%></td>
            <td><%=ls.getNomActivite()%></td>
            <td class="py-3">
                <span class="badge badge-pill badge-success">Verified</span>
            </td>
            <td class="py-3">
                <div class="position-relative">
                    <a class="link-dark d-inline-block" href="#">
                        <i class="gd-pencil icon-text"></i>
                    </a>
                    <a class="link-dark d-inline-block" href="#">
                        <i class="gd-trash icon-text"></i>
                    </a>
                </div>
            </td>
        </tr>
        <% } %>
    </table>
  </div>
</div>
<!-- End form -->