document.addEventListener('DOMContentLoaded', () => {
    // --- Timer Logic ---
    let hours = 0, minutes = 0, seconds = 0, timerInterval = null;
    const hEl = document.getElementById('hours');
    const mEl = document.getElementById('minutes');
    const sEl = document.getElementById('seconds');
    const startBtn = document.getElementById('startBtn');
    const pauseBtn = document.getElementById('pauseBtn');
    const resetBtn = document.getElementById('resetBtn');

    function updateDisplay() {
        hEl.textContent = String(hours).padStart(2, '0');
        mEl.textContent = String(minutes).padStart(2, '0');
        sEl.textContent = String(seconds).padStart(2, '0');
    }

    function tick() {
        seconds++;
        if (seconds === 60) { seconds = 0; minutes++; }
        if (minutes === 60) { minutes = 0; hours++; }
        updateDisplay();
    }

    startBtn.addEventListener('click', () => {
        if (!timerInterval) timerInterval = setInterval(tick, 1000);
    });
    pauseBtn.addEventListener('click', () => {
        clearInterval(timerInterval);
        timerInterval = null;
    });
    resetBtn.addEventListener('click', () => {
        clearInterval(timerInterval);
        timerInterval = null;
        hours = minutes = seconds = 0;
        updateDisplay();
    });

    // --- Calendar Logic (Luxon 사용) ---
    const DateTime = luxon.DateTime;
    let current = DateTime.local();

    const monthYearEl = document.getElementById('monthYear');
    const calendarBody = document.getElementById('calendarBody');
    const prevMonthBtn = document.getElementById('prevMonth');
    const nextMonthBtn = document.getElementById('nextMonth');

    function renderCalendar(dt) {
        monthYearEl.textContent = dt.toLocaleString({ month: 'long', year: 'numeric' });
        // 첫째 날 요일, 마지막 일수
        const first = dt.startOf('month');
        const last = dt.endOf('month').day;
        const startWeekday = first.weekday % 7; // Luxon: Mon=1..Sun=7
        calendarBody.innerHTML = '';
        let row = document.createElement('tr');
        // 빈 칸 채우기
        for (let i = 0; i < startWeekday; i++) {
            row.appendChild(document.createElement('td'));
        }
        for (let day = 1; day <= last; day++) {
            if (row.children.length === 7) {
                calendarBody.appendChild(row);
                row = document.createElement('tr');
            }
            const cell = document.createElement('td');
            cell.textContent = day;
            row.appendChild(cell);
        }
        // 마지막 줄 빈 칸
        while (row.children.length < 7) {
            row.appendChild(document.createElement('td'));
        }
        calendarBody.appendChild(row);
    }

    prevMonthBtn.addEventListener('click', () => {
        current = current.minus({ months: 1 });
        renderCalendar(current);
    });
    nextMonthBtn.addEventListener('click', () => {
        current = current.plus({ months: 1 });
        renderCalendar(current);
    });

    // 초기 렌더링
    updateDisplay();
    renderCalendar(current);
});
