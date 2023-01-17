export async function postHabit({ data, cookie }) {
  try {
    const response = await axios
      .post(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits`, data, {
        headers: {
          Authorization: cookie,
        },
      })
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getHabitDetail({ habitId }) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}`)
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteHabit({ cookie, habitId }) {
  try {
    const response = await axios
      .delete(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}`, {
        headers: {
          Authorization: cookie,
        },
      })
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getHabitAuths({ habitId, page, size }) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths?page=${page}&size=${size}`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function postAuthReport({
  habitId,
  authReportPostDto,
  cookie,
  authId,
}) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/${authId}/reports`,
        authReportPostDto,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postBookMark({ habitId, cookie, userId }) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/bookmarks?userId=${userId}`,
        null,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteBookMark({ habitId, cookie, userId }) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/bookmarks?userId=${userId}`,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postStartChallenge({ habitId, cookie, userId }) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/challenges?userId=${userId}`,
        null,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postHabitReport({ habitId, cookie, userId }) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reports`,
        { habitId, reportType: 'ABUSE', reporter: userId },
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getHabitReviews({ habitId, page, size }) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews?page=${page}&size=${size}`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postHabitReview({
  habitId,
  cookie,
  userId,
  body,
  score,
}) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews?userId=${userId}`,
        { body, score },
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteHabitReview({ habitId, cookie, reviewId }) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}`,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchHabitReview({
  habitId,
  cookie,
  reviewId,
  body,
  score,
}) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}`,
        { body, score, reviewId },
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postHabitReviewReport({ habitId, cookie, reviewId }) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}/reports`,
        {},
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getHabitStatistics({ habitId }) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/statistics`)
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getHabitsSearch({ keyword, page, size }) {
  try {
    const response = await axios
      .get(
        `${
          process.env.NEXT_PUBLIC_SERVER_URL
        }/habits/search?page=${page}&size=${size}${
          keyword === undefined ? null : `&keyword=${keyword}`
        }`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getHabitsSearchCategory({ categoryId, page, size }) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search/${categoryId}/?page=${page}&size=${size}`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
