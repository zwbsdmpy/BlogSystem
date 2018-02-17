/**
 * 创建类别
 * @param funWhenCreateCategorySuccess 创建成功时回调，回传参数为新标签id
 * @param funWhenCreateCategoryFail 创建失败时回调，参数为错误码和错误信息
 */
function createCategory(funWhenCreateCategorySuccess, funWhenCreateCategoryFail) {
    var title = $('#categoryTitle').val();
    var bewrite = $('#categoryBewrite').val();

    if (isStrEmpty(title)) {
        error('类别名称不能为空', 'categoryErrorMsg');
        return;
    } else {
        $('#categoryErrorMsg').html(' ');
    }

    disableButton(false, 'newCategoryBtn', '正在创建...');
    $.post(
        '/blogger/' + pageOwnerBloggerId + '/category',
        {title: title, bewrite: bewrite},
        function (result) {
            if (result.code === 0) {
                disableButton(false, 'newCategoryBtn', '创建成功');
                funWhenCreateCategorySuccess(result.data);

                setTimeout(function () {
                    disableButton(true, 'newCategoryBtn', '创建');
                    $('#newCategoryDialog').modal('toggle');
                }, 1000);

            } else {
                error(result.msg, 'categoryErrorMsg');
                disableButton(true, 'newCategoryBtn', '创建');

                funWhenCreateCategoryFail(result);
            }
        }, 'json'
    );

}
