

setTimeout(() => {
    const box = document.getElementById('loading');

    // 👇️ removes element from DOM
    box.style.display = 'none';

    // 👇️ hides element (still takes up space on page)
    // box.style.visibility = 'hidden';
}, 1800); // 👈️ time in milliseconds