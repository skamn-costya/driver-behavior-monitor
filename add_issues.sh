#!/bin/bash

# Define your project name and the target column.
PROJECT_NAME="MVP Development Plan — Driver Behavior Monitor"

# Check if the CSV file exists
if [ ! -f "12_day_plan.csv" ]; then
    echo "Error: issues.csv not found."
    exit 1
fi

# Use `tail -n +2` to skip the header row of the CSV
cat 12_day_plan.csv | while IFS=, read -r title description column
do
  # Create a new issue and link it to the project.
  echo "Creating issue: $title"
  ISSUE_URL=$(gh issue create \
    --title "$title" \
    --body "$description" \
    --project "$PROJECT_NAME")

  # Extract the issue number from the URL
#  ISSUE_NUMBER=$(echo "$ISSUE_URL" | sed 's/.*\/issues\///')

  # Add the newly created issue to the specified column.
  echo "Adding issue #$ISSUE_NUMBER to project '$PROJECT_NAME' column '$column'"
#  gh project item-add \
#    --project "$PROJECT_NAME" \
#    --issue-id "$ISSUE_NUMBER" \
#    --column "$column"
done

echo "Done! All issues from the CSV have been created and added to the '$COLUMN_NAME' column."
