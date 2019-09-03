data = csvread('error-stats-matlab.csv');

% Normalise data
% mu = median(data(:, end));
% x = data(:, end) - mu;
% sig = sqrt(x' * x / length(data(:, end)));
% idx = data(:, end) >= mu + 0.25 * sig;
% data(idx, end) = max(data(~idx, end));
lo = min(data(:, end));
temp = (data(:, end) - lo) / lo;
idx = data(:, end) > 0.4;
data(idx, end) = max(data(~idx, end));

filler = max(data(:, end));
% Work out scale
[~, scale] = kmeans(data(:, end), 10);
scale = sort(scale);
scale(1) = min(data(:, end));
scale(end) = filler;
scale = floor(scale * 100 + 0.5) / 100;
% Display meshgrid
[X, Y] = meshgrid(0 : 0.1 : 1.0, 0.1 : 0.1 : 1.0);

currentFigure = 0;
for deathCount = 1 : 10
    deathRate = deathCount * 0.1;
    workIdx = find(abs(data(:, 1) - deathRate) < 1e-5);
    workData = data(workIdx, 2 : end);
    replaceIdx  = workData(:, 1) * 10 + 1;
    mutationIdx = workData(:, 2) * 10 + 1;
    Z = ones(10, 11) * filler;
    Z(sub2ind(size(Z), replaceIdx, mutationIdx)) = workData(:, end);
    
    figureID = mod((deathCount - 1), 4) + 1;
    if (figureID == 1)
        currentFigure = currentFigure + 1;
    end
    
    figure(currentFigure);
    subplot(2, 2, figureID);
    contourf(X, Y, Z, scale, 'ShowText', 'On');
    title(sprintf('Death rate = %.1f', deathRate));
end 
