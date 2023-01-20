import axios from 'axios';
import type {
  habitGeneralProps,
  postHabitProps,
  deleteHabitProps,
  getHabitAuthsReviewsProps,
  postAuthReportProps,
  postHabitReviewProps,
  deleteHabitReviewProps,
  patchHabitReviewProps,
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
export async function getHabitDetail(habitId: string) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}`)
      .then((res) => console.log(res));
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
export async function getHabitAuths({
  habitId,
  page,
  size,
}: getHabitAuthsReviewsProps) {
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

// 문서와 좀 다름 -> 추후 수정 필요
export async function postAuthReport({
  habitId,
  authReportPostDto,
  authId,
}: postAuthReportProps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/${authId}/reports`,
        authReportPostDto,
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
export async function postHabitReport({ habitId, userId }: habitGeneralProps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reports`,
        { habitId, reportType: 'ABUSE', reporter: userId },
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
export async function getHabitReviews({
  habitId,
  page,
  size,
}: getHabitAuthsReviewsProps) {
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
  userId,
  body,
  score,
}: postHabitReviewProps) {
  console.log(habitId, userId, body, score);
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews?userId=${userId}`,
        { body, score },
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
export async function deleteHabitReview({
  habitId,
  reviewId,
}: deleteHabitReviewProps) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}`,
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
export async function patchHabitReview({
  habitId,
  reviewId,
  body,
  score,
}: patchHabitReviewProps) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}`,
        { body, score, reviewId },
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
export async function postHabitReviewReport({
  habitId,
  reviewId,
}: deleteHabitReviewProps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}/reports`,
        {},
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
