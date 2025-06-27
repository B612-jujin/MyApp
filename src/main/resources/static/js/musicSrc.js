// musicSrc.js
document.addEventListener('DOMContentLoaded', () => {
  console.log('▶ musicSrc.js loaded');

  // 요소 조회
  const fileBtn     = document.getElementById('fileBtn');
  const urlBtn      = document.getElementById('urlBtn');
  const filePicker  = document.getElementById('filePicker');
  const urlInput    = document.getElementById('urlInput');
  const setUrlBtn   = document.getElementById('setUrlBtn');
  const sourceInfo  = document.getElementById('sourceInfo');
  const lengthEl    = document.querySelector('.text-strong2');
  const ytContainer = document.getElementById('youtubeContainer');
  const player      = document.getElementById('player');
  const filterMenu  = document.querySelector('.filter-menu');

  // File 버튼 클릭 → 파일 선택창
  fileBtn.addEventListener('click', () => {
    urlInput.style.display    = 'none';
    setUrlBtn.style.display   = 'none';
    ytContainer.style.display = 'none';
    player.style.display      = 'none';
    filePicker.value          = '';
    filePicker.click();
  });

  // 파일 선택 후 처리
  filePicker.addEventListener('change', e => {
    const file = e.target.files[0];
    if (!file) return;

    // UI 초기화
    sourceInfo.textContent      = file.name;
    lengthEl.textContent        = '로딩 중...';
    filterMenu.classList.remove('dark');
    player.style.display        = 'block';
    ytContainer.style.display   = 'none';

    // Blob URL 재생
    const blobUrl = URL.createObjectURL(file);
    player.src = blobUrl;
    player.load();
    player.play().catch(() => {});

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

  // URL 버튼 클릭 → 입력창 표시
  urlBtn.addEventListener('click', () => {
    urlInput.style.display     = 'inline-block';
    setUrlBtn.style.display    = 'inline-block';
    filePicker.style.display   = 'none';
    player.style.display       = 'none';
    ytContainer.style.display  = 'none';
    filterMenu.classList.remove('dark');
  });

  // URL 설정 후 처리
  setUrlBtn.addEventListener('click', () => {
    console.log('▶ setUrlBtn clicked');
    const url = urlInput.value.trim();
    if (!url) return alert('URL을 입력해주세요.');

    sourceInfo.textContent = url;
    lengthEl.textContent   = '';

    // 배경을 서서히 검정으로
    filterMenu.classList.add('dark');
    console.log('▶ filterMenu.classList.add("dark")', filterMenu.classList);
    // 오디오 파일이면 직접 재생
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
        iframe.src         = `https://www.youtube.com/embed/${m[1]}?autoplay=1`;
        iframe.allow       = 'autoplay; encrypted-media';
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

    // 입력창과 설정 버튼 숨기기
    urlInput.style.display  = 'none';
    setUrlBtn.style.display = 'none';
  });
});
