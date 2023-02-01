import axios, { AxiosError } from 'axios';
import type {
  habitGeneralProps,
  deleteHabitProps,
  getHabitsSearchProps,
  getHabitsSearchCategoryProps,
  getHabitsInHomeProps,
  patchHabitDetailProps,
} from './moduleInterface';

export async function postHabit({ data }) {
  try {
    const response = await axios
      .post(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits`, data, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((res) => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getHabitDetail({ habitId, userId }: habitGeneralProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}${
          userId ? `?userId=${userId}` : ''
        }`,
      )
      .then((res) => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchHabitDetail({
  habitId,
  userId,
  data,
}: patchHabitDetailProps) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}?userId=${userId}`,
        data,
      )
      .then((res) => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function deleteHabit({ habitId }: deleteHabitProps) {
  try {
    const response = await axios
      .delete(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}`)
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function postBookMark({ habitId, userId }: habitGeneralProps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/bookmarks?userId=${userId}`,
        null,
      )
      .then((res) => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteBookMark({ habitId, userId }: habitGeneralProps) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/bookmarks?userId=${userId}`,
      )
      .then((res) => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postStartChallenge({
  habitId,
  userId,
}: habitGeneralProps) {
  try {
    const response: number = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/challenges?userId=${userId}`,
        null,
      )
      .then((res) => res.status);
    return response;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}
export async function getHabitStatistics(habitId: number) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/statistics`)
      .then((res) => res.data);
    return response;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}
export async function getHabitsSearch({
  keyword,
  page,
  size,
}: getHabitsSearchProps) {
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
export async function getHabitsSearchCategory({
  categoryId,
  page,
  size,
}: getHabitsSearchCategoryProps) {
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

export async function getHabitsInHome({
  userId,
  type,
  page,
  size,
}: getHabitsInHomeProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/sort/${type}?${
          userId ? 'userId=' + userId + '&' : ''
        }page=${page}&size=${size}`,
      )
      .then((res) => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}
