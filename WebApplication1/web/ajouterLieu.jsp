<div class="mb-3 mb-md-4 d-flex justify-content-between">
    <div class="h3 mb-0">Ajouter Lieu</div>
</div>


<!-- Form -->
<div>
    <form action="LieuServlet" method="post">
        <div class="form-row">
            <div class="form-group col-12 col-md-6">
                <label for="nomLieu">nomLieu</label>
                <input type="text" class="form-control" value="" id="nomLieu" name="nomLieu" placeholder="nom Lieu">
            </div>
            <div class="form-group col-12 col-md-6">
                
            </div>
        </div>
        <div class="custom-control custom-switch mb-2">
            <input type="checkbox" class="custom-control-input" id="randomPassword">
            <label class="custom-control-label" for="randomPassword">Set Random Password</label>
        </div>

        <button type="submit" class="btn btn-primary float-right">Create</button>
    </form>
</div>
<!-- End Form -->