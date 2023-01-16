export async function postHabit({ data, cookie }) {
  try {
    const response = await axios
      .post(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits`, data, {
        headers: {
          Authorization: cookie,
        },
      })
      .then((res) => res.status);
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
export async function postAuth({ challengeId, habitId, authPostDto, cookie }) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths`,
        authPostDto,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => res.status);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteAuth({ cookie, habitId, authId }) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/${authId}`,
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
export async function patchAuths({ authId, authPatchDto, cookie, habitId }) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/${authId}`,
        authPatchDto,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => res.status);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postAuthReport({
  challengeId,
  habitId,
  authPostDto,
  cookie,
}) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths`,
        authPostDto,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => res.status);
    return response;
  } catch (e) {
    console.error(e);
  }
}
