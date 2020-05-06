

export default async function getRecipes(include, exclude, pantry, count) {
  const API_URL = "http://localhost:4567/b/recipe";

  const params = {"include": include, "exclude": exclude, "pantry":pantry, "count":count}
   return fetch(API_URL, {
    method: "POST",
    headers: {
      "content-type": "application/json"
    },
    body: params
  })
    .then(res => {return res.json()})
    .catch("Error: Something went wrong.");
}

