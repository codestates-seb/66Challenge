import axios from 'axios';
import type {
  habitGeneralProps,
  postHabitProps,
  deleteHabitProps,
  getHabitsSearchProps,
  getHabitsSearchCategoryProps,
} from './moduleInterface';
import { getCookie } from './cookies';
export async function postHabit({ data }: postHabitProps) {
  try {
    const response = await axios
      .post(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits`, data, {
        headers: {
          Authorization: getCookie('accessJwtToken'),
        },
      })
      .then((res) => console.log(res));
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
export async function deleteHabit({ habitId }: deleteHabitProps) {
  try {
    const response = await axios
      .delete(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}`, {
        headers: {
          Authorization: getCookie('accessJwtToken'),
        },
      })
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
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
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
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
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
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/challenges?userId=${userId}`,
        null,
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function getHabitStatistics(habitId: string) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/statistics`)
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
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
