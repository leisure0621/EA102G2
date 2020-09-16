document.addEventListener(
  'touchstart',
  function (event) {
    // 判斷默認行為是否可以被禁用
    if (event.cancelable) {
      // 判斷默認行為是否已經被禁用
      if (!event.defaultPrevented) {
        event.preventDefault();
      }
    }
  },
  false
);
