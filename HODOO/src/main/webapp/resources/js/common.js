function move_url(url) {
	document.location.href = url;
}
function movePage(pageNum) {
	$('#currentPage').val(pageNum);
	$('#searchForm').submit();
}