export const DateFormat = (date: string) => {
  const day: number = 1000 * 60 * 60 * 24;
  const hour: number = 1000 * 60 * 60;
  const minute: number = 1000 * 60;

  const targetDate: number = new Date(date.slice(0, 19)).getTime();
  const todayDate: number = new Date().getTime();
  const dateDiff: number = todayDate - targetDate;

  const diffDay: number = Math.floor(dateDiff / day);

  if (diffDay >= 7) {
    return date.slice(0, 10);
  } else if (1 <= diffDay && diffDay < 7) {
    return `${diffDay}일 전`;
  }

  const diffHour: number = Math.floor((dateDiff - diffDay * day) / hour);
  if (diffHour >= 1) {
    return `${diffHour}시간 전`;
  }
  const diffMinute: number = Math.floor(
    (dateDiff - diffDay * day - diffHour * hour) / minute,
  );
  if (diffMinute >= 1) {
    return `${diffMinute}분 전`;
  } else {
    return `방금`;
  }
};
