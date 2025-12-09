/* タブ切り替え */
document.querySelectorAll('.tab').forEach(tab => {
  tab.addEventListener('click', () => {
    document.querySelector('.tab.active').classList.remove('active');
    tab.classList.add('active');

    document.querySelector('.tab-content.active').classList.remove('active');
    document.getElementById("tab-" + tab.dataset.tab).classList.add('active');
  });
});

/* マイデータ 保存（疑似処理） */
document.getElementById('mydata-form').addEventListener('submit', function (e) {
  e.preventDefault();

  // 本来は fetch("/api/save") などで保存する
  // ここではUI表示のみ
  const flash = document.getElementById('flash-message');
  flash.classList.remove('hidden');

  // 3秒後に非表示
  setTimeout(() => flash.classList.add('hidden'), 3000);
});
