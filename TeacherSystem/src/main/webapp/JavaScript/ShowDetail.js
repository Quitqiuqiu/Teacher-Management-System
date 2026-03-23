console.log('✅ ShowDetail.js 已执行');

const teacherModal = document.getElementById('teacherDetailModal');

if (!teacherModal) {
    console.error('❌ 未找到 teacherDetailModal');
} else {

    teacherModal.addEventListener('show.bs.modal', function (event) {

        const triggerBtn = event.relatedTarget;
        if (!triggerBtn) return;

        // 读取 data-*
        const workId = triggerBtn.getAttribute('data-workid');
        const name   = triggerBtn.getAttribute('data-name');
        const gender = triggerBtn.getAttribute('data-gender');
        const school = triggerBtn.getAttribute('data-school');
        const title  = triggerBtn.getAttribute('data-title');

        console.log('📘 加载教师：', name, workId);

        // 填充基础信息
        document.getElementById('modalWorkId').textContent = workId || '-';
        document.getElementById('modalName').textContent = name || '-';
        document.getElementById('modalGender').textContent = gender || '-';
        document.getElementById('modalSchool').textContent = school || '-';
        document.getElementById('modalTitle').textContent = title || '-';

        // 去评价按钮
        const rateBtn = document.getElementById('modalRateBtn');
        rateBtn.href = `${contextPath}/comment?action=toAdd&teacherId=${workId}`;
        console.log('🔗 去评价链接：', rateBtn.href);

        // 异步加载平均分
        const scoreElement = document.getElementById('modalAverageScore');
        if (!scoreElement) return;

        scoreElement.innerHTML = '<span class="text-muted">加载中...</span>';

        fetch(`${contextPath}/comment?action=getAverage&teacherId=${workId}`)
            .then(response => response.text())
            .then(avg => {
                if (!avg || avg === '0') {
                    scoreElement.innerHTML = '<span class="text-muted">暂无评分</span>';
                } else {
                    scoreElement.innerHTML = `<b class="text-danger">${avg}</b> / 5.0`;
                }
            })
            .catch(err => {
                console.error('❌ 平均分获取失败', err);
                scoreElement.innerHTML = '<span class="text-muted">暂无评分</span>';
            });
    });
}