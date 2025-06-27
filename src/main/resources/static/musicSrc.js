// musicSrc.js
document.addEventListener('DOMContentLoaded', () => {
  const fileBtn     = document.getElementById('fileBtn');
  const urlBtn      = document.getElementById('urlBtn');
  const filePicker  = document.getElementById('filePicker');
  const urlInput    = document.getElementById('urlInput');
  const setUrlBtn   = document.getElementById('setUrlBtn');
  const sourceInfo  = document.getElementById('sourceInfo');
  const player      = document.getElementById('player');
  const ytContainer = document.getElementById('youtubeContainer');
  const lengthEl    = document.querySelector('.text-strong2');
  const enableimg = document.getElementById('enableimg');

  // 파일 선택 버튼 클릭
  fileBtn.addEventListener('click', () => {
    urlInput.style.display   = 'none';
    setUrlBtn.style.display  = 'none';
    ytContainer.style.display = 'none';
    player.style.display     = 'none';
    filePicker.value = '';
    filePicker.click();
  });

  // 파일 선택 후 처리
  filePicker.addEventListener('change', e => {
    const file = e.target.files[0];
    if (!file) return;
    sourceInfo.textContent = file.name;
    lengthEl.textContent   = '로딩 중...';
    enableimg.style.visibility = "visible";
    const blobUrl = URL.createObjectURL(file);
    player.src = blobUrl;
    player.style.display = 'block';
    ytContainer.style.display = 'none';
    player.load();
    player.play().catch(() => { /* 자동재생 차단 무시 */ });

    player.addEventListener('loadedmetadata', () => {
      const d = player.duration;
      const m = Math.floor(d / 60);
      const s = String(Math.floor(d % 60)).padStart(2, '0');
      lengthEl.textContent = `${m}:${s}`;
    }, { once: true });

    // 메모리 해제
    const revoke = () => URL.revokeObjectURL(blobUrl);
    player.addEventListener('ended', revoke, { once: true });
    filePicker.addEventListener('change', revoke, { once: true });
  });


  // URL 입력 버튼 클릭
  urlBtn.addEventListener('click', () => {
    enableimg.style.display = 'none';
    filePicker.style.display  = 'none';
    player.style.display      = 'none';
    ytContainer.style.display = 'none';
    urlInput.style.display    = 'inline-block';
    setUrlBtn.style.display   = 'inline-block';
  });

  // URL 설정 후 처리
  setUrlBtn.addEventListener('click', () => {
    const url = urlInput.value.trim();
    if (!url) return alert('URL을 입력해주세요.');

    sourceInfo.textContent = url;
    lengthEl.textContent   = '';

    // 오디오 파일 확장자 체크
    if (/\.(mp3|wav|ogg)(\?.*)?$/i.test(url)) {
      ytContainer.style.display = 'none';
      player.src = url;
      player.style.display = 'block';
      player.load();
      player.play().catch(() => {});

      player.addEventListener('loadedmetadata', () => {
        const d = player.duration;
        const m = Math.floor(d / 60);
        const s = String(Math.floor(d % 60)).padStart(2, '0');
        lengthEl.textContent = `${m}:${s}`;
      }, { once: true });
    } else {
      // YouTube 임베드
      player.pause();
      player.style.display = 'none';
      ytContainer.innerHTML = '';

      const m = url.match(/(?:v=|\.be\/)([A-Za-z0-9_-]{11})/);
      if (m) {
        const iframe = document.createElement('iframe');
        iframe.src = `https://www.youtube.com/embed/${m[1]}?autoplay=1`;
        iframe.allow = 'autoplay; encrypted-media';
        iframe.frameBorder = '0';
        iframe.style.width  = '100%';
        iframe.style.height = '315px';
        ytContainer.appendChild(iframe);
        ytContainer.style.display = 'block';
      } else {
        ytContainer.textContent = '유효한 YouTube 링크가 아닙니다.';
        ytContainer.style.display = 'block';
      }
    }
  });
});
